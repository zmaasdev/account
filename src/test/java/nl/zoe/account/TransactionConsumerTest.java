package nl.zoe.account;

import nl.zoe.account.cloud.function.event.TransactionEvent;
import nl.zoe.account.model.Account;
import nl.zoe.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.kafka.consumer.auto-offset-reset=earliest",
        "spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer",
        "spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer",
        "spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer",
        "spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer",
        "spring.kafka.consumer.properties.spring.json.trusted.packages=nl.zoe.account.cloud.function.event"
})
@Testcontainers
public class TransactionConsumerTest {

    @Container
    static final KafkaContainer kafka = new KafkaContainer(
            DockerImageName.parse("apache/kafka:latest")
    );

    @DynamicPropertySource
    static void registerKafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private AccountRepository accountRepository;
    private String accountId;

    @BeforeEach
    void setUp() {
        Account account = new Account();
        account.setCustomerId("test_customer_id");
        Account savedAccount = accountRepository.save(account);
        accountId = savedAccount.getId();
    }

    @Test
    public void testConsumer() {
        String customerId = "test_customer_id";
        TransactionEvent event = new TransactionEvent(accountId, new BigDecimal("1000"));
        kafkaTemplate.send("transaction-topic", customerId, event);

        await()
            .pollInterval(Duration.ofSeconds(3))
            .atMost(10, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                Optional<Account> optionalTransactionEvent = accountRepository.findById(accountId);
                assertThat(optionalTransactionEvent).isPresent();
                assertThat(optionalTransactionEvent.get().getCustomerId()).isEqualTo(customerId);
                assertThat(optionalTransactionEvent.get().getBalance()).isEqualTo(new BigDecimal("1000.00"));
            });
    }
}
