import ch.qos.logback.classic.encoder.PatternLayoutEncoder

appender("STDOUT", ConsoleAppender) {
	encoder(PatternLayoutEncoder) {
		pattern = "%-50(%date{ISO8601} [%thread]) %-5level %logger{50} - %msg%n%rEx"
	}
}

logger('org.springframework', ERROR, ['STDOUT'], false)
logger('org.mortbay.http', ERROR, ['STDOUT'], false)
logger('org.apache.curator', ERROR, ['STDOUT'], false)
logger('org.apache.zookeeper', ERROR, ['STDOUT'], false)
logger('com.dealertrack.admiral', ERROR, ['STDOUT'], false)

root(INFO, ["STDOUT"])