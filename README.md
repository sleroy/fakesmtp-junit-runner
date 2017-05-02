# fakesmtp-junit-runner

[![Build Status](https://travis-ci.org/sleroy/fakesmtp-junit-runner.svg?branch=master)](https://travis-ci.org/sleroy/fakesmtp-junit-runner)


This module is an extension to JUnit to help developers to write integration tests of their mail services inside Java webapps.

Basically, it's a JUnit Rule (@Rule). 

The idea is :


* Inserts the @Rule in your integration tests
* a Fake SMTP Server will start
* You can send mails on it
* You can control the mailbox 
* Write your owyn assertions to check mails.

To use it :


