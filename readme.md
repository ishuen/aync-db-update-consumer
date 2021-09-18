readme
======

## Overview

This is a project utilize rabbitmq to asynchronously update the database with the data collected from a websocket stream.

There are 2 repositories involved. One is the producer repo **async-db-update-producer** and the other is the consumer repo **async-db-update-consumer**.


## Procedure breakdown
1. Producer application establishes a websocket connection with Alpaca server and then completes the authentication process.
2. Producer application subscribes to the stock price real time data.
3. In order not to let the sudden data surge caused by the market spike effect the application performance, the producer does not handle synchronous functions such as data tuple insertion. The producer pushes events to the message queue.
4. The rabbitMq server routes the events to the consumer application.
5. The consumer application receives the event and transform the data to the object.
6. The consumer application inserts the data to the database.

## Data source
[US stock market data from Alpaca websocket stream](https://alpaca.markets/docs/api-documentation/api-v2/streaming/)

## Schema

Sample data from the subscribed stream:

```
[{"T":"t","i":96921,"S":"AAPL","x":"D","p":126.55,"s":1,"t":"2021-02-22T15:51:44.208Z","c":["@","I"],"z":"C"}]
```
| Attribute | Data type | description              |
|-----------|-----------|--------------------------|
| T         | string    | message type, always “t” |
| S         | string    | symbol                   |
| i         | int       | trade ID                 |
| x         | string    | exchange code where the trade occurred |
| p         | decimal   | trade price              |
| s         | int       | trade size               |
| t         | string    | RFC-3339 formatted timestamp with nanosecond precision. |
| c         | string[]  | trade condition          |
| z         | string    | tape                     |

The corresponding relational database schema is as below:

Table name: trade_data

| Column name | data type |
|-------------|-----------|
| symbol      | char(10)  |
| trade_id    | int       |
| exchange_code | char(10) |
| price       | decimal   |
| size        | int       |
| timestamp   | timestamp |

## Getting start

1. Preparation

	- Prepare a Alpaca paper account
	- Rename `key.sample.config` file to `key.config` and Specify the API key and secret key in the configuration file
	

2. Run rabbitmq server locally

```
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.9-management
```

3. Run DB server

4. Run consumer application

5. Run producer application

