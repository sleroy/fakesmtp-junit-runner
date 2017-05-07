# fakesmtp-junit-runner

[![Build Status](https://travis-ci.org/sleroy/fakesmtp-junit-runner.svg?branch=master)](https://travis-ci.org/sleroy/fakesmtp-junit-runner)

[![Coverage Status](https://coveralls.io/repos/sleroy/fakesmtp-junit-runner/badge.svg?branch=master&service=github)](https://coveralls.io/github/sleroy/fakesmtp-junit-runner?branch=master)

[![Javadocs](https://www.javadoc.io/badge/sleroy/fakesmtp-junit-runner.svg)](https://www.javadoc.io/doc/sleroy/fakesmtp-junit-runner)


**Important** : Part of the source code of this library has been modified and adapted from the project of [FakeSmtp](https://github.com/Nilhcem/FakeSMTP). I want to thank him since his project inspired me the creation of that library.

This library is an extension to JUnit to allow developers to write integration tests where a SMTP server is required.

The how-to is quite simple :

* Inserts the @Rule in your integration tests
* a Fake SMTP Server will start
* You can send mails on it
* You can control the mailbox 
* Write your own assertions to check mails.

## Installation

The project requires JUnit 4.11 or higher. It also requires SLF4J API presents in the classpath. I did not bundle them in the library to avoid conflicts.

To use it, adds the library to your maven or gradle config script :

**For maven :**

```
<dependency>
  <groupId>com.github.sleroy</groupId>
  <artifactId>fakesmtp-junit-runner</artifactId>
  <version>0.1.1</version>
  <scope>test</scope>
</dependency>
```

**For gradle :**

```
testCompile "com.github.sleroy:fakesmtp-junit-runner:0.1.1"
```

## Usage

**Step 1 :**

Creates a JUnit test :

```
public class SmtpSendingClassTest {


  @Test
  public void testCase1() {

  }

}
```

**Step 2 :**

Adds the new Junit rule with its configuration :

```
public class SmtpSendingClassTest {

  @Rule
	public FakeSmtpRule smtpServer = new FakeSmtpRule(ServerConfiguration.create().port(2525).charset("UTF-8"));

  @Test
  public void testCase1() {

  }

}
```

**Step 3 :**

You are ready to use it, controls the mailbox or the server state :

```
Assert.assertTrue(smtpServer.isRunning());
```

```
public class SmtpSendingClassTest {

  @Rule
	public FakeSmtpRule smtpServer = new FakeSmtpRule(ServerConfiguration.create().port(2525).charset("UTF-8"));

  @Test
  public void testCase1() {
    Assert.assertTrue(smtpServer.isRunning());
    Assert.assertTrue(smtpServer.mailbox().isEmpty());
  }

}
```

