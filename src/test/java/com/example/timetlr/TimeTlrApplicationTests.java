package com.example.timetlr;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.print.attribute.standard.Destination;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.app.tasklaunchrequest.DataFlowTaskLaunchRequestAutoConfiguration;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"task.launch.request.task_name=foo"})
public class TimeTlrApplicationTests {

	@Test
	public void contextLoads() throws IOException {
		try (ConfigurableApplicationContext context = new SpringApplicationBuilder(
			TestChannelBinderConfiguration.getCompleteConfiguration(TimeTlrApplication.class)).web(WebApplicationType.NONE)
			.run("--spring.jmx.enabled=false", "--task.launch.request.task-name=foo")) {

			OutputDestination output = context.getBean(OutputDestination.class);
			ObjectMapper objectMapper = context.getBean(ObjectMapper.class);

			Message<byte[]> message = output.receive(2000);
			assertThat(message).isNotNull();

			DataFlowTaskLaunchRequestAutoConfiguration.DataFlowTaskLaunchRequest request =
				objectMapper.readValue(message.getPayload(),
					DataFlowTaskLaunchRequestAutoConfiguration.DataFlowTaskLaunchRequest.class);

			assertThat(request.getTaskName()).isEqualTo("foo");

		}
	}

}
