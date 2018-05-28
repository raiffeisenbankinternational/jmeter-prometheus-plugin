# jmeter-prometheus-plugin

To use plugin execute:

```
mvn clean package
```

And copy all *.jar file from target folder int your jMeter /lib/ext directory
To use plugin add Prometheus Status Exporter to your Test Plan

![Screenshot](documentation/images/jmeter-listener.png)


It exposes pre default port 8080 and url http://<your-server>:8080/metrics will output current metrics. 
