## Timeline
 * upgrade LINUX // JAVA // IDEA 4h
 * setup environment 1h
 * setup csrf token 2h + login registration
 * database structure setup 1h
 * create Hotel part 3h
 * create // cleanup and push to git 30m
 * added record submitting engine 1h
 * added validation error handling and messages 1h
 * added hotel deletion entity injection 1h
 * started to implement hotel room 1h
 * created Room part 2h
 * added reservation logic 3h
 * tested fixed findings added basic tests. 1.5h

## JAVA setup guilde (LINUX // ubuntu) (Required only for IDEA)
 * ```cd ~/Downloads```
 * ```wget https://download.oracle.com/java/23/latest/jdk-23_linux-x64_bin.deb```
 * ```sudo apt install -f ~/Downloads/jdk-23_linux-x64_bin.deb```
 * ```update-java-alternatives --list```
 * ```sudo update-java-alternatives --set /usr/lib/jvm/jdk-23.0.1-oracle-x64```

## INSTALLATION
 * ```docker compose up -d``` - from sources root
 * without docker feel free to ask directly https://www.linkedin.com/in/ml-698627181/ (neznajki@gmail.com)

## HOW TO
* first action is create hotel
* second is create room inside this hotel
* then you can start reservation or searching rooms

## Motivation
 * created template engine (to show that I can create complex mechanisms https://www.baeldung.com/spring-template-engines)
 * validation inherited from other project to not spend time for investigation or rework >> https://www.baeldung.com/spring-boot-bean-validation, hope to integrate it for new functionality
 * csrf validation not finished because, spend too much time for machine setup don't have time to finish it, so just disabled.
 * won't have time to make cure reservation cancellation so will make only on daily basis

## TODO hope will finish one day
 * add TESTS
 * make reservation review and deletion