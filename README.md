# Drift Trade Client for Java

A lightweight Java library designed to interface with the Drift decentralized exchange (DEX). This bridge provides a seamless way to trigger crypto market actions via Java endpoints, enabling programmatic trading and account management.

## Features

* **Order/Position Management**: Create and cancel orders/positions on the Drift market.
* **Balance Verification**: Programmatically check account balances and asset positions.
* **API Integration**: Simplified wrapper for Drift's REST API.
* **Websocket Update**: Subscribe to real-time order books, trades, and account updates
* **Java Native**: Built for easy integration into existing Spring Boot or standalone Java applications.

## Getting Started

### Prerequisites

* JDK 11 or higher
* Maven
* Drift Wallet credentials

### Installation

Clone the repository and install it to your local Maven repository:

```bash
git clone https://github.com/Wayruha/drift-trade-client.git
cd drift-trade-client
mvn clean install
```

### Examples

You can find simple examples in `src/test` directory.