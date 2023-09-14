![saga-orchestration.png](..%2F..%2F..%2FDownloads%2Fsaga-orchestration.png)

## Saga Orchestration
run 'chmod +x ./run.sh' to make the script executable
run './run.sh' for start kafka

run 'chmod +x ./stop.sh' to make the script executable
run './stop.sh' for stop kafka


### What is Saga Orchestration?

Saga Orchestration is a pattern for managing failures in a distributed system. It is a sequence of local transactions. Each local transaction updates the database and publishes a message or event to trigger the next local transaction in the saga. If a local transaction fails because it violates a business rule then the saga executes a series of compensating transactions that undo the changes that were made by the preceding local transactions.

### The saga's component about:
