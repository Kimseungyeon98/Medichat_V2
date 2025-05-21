package ksy.medichat.member.email;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
public class Email {
	private String subject;
	private String content;
	private String receiver;
}
