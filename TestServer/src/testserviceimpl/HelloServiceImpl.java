package testserviceimpl;

import testservice.HelloService;

public class HelloServiceImpl implements HelloService {

	public String hello() {
		System.out.println("Hello from Server");
		return "Hello2";
	}

}
