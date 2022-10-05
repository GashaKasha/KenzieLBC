# ATA-Learn-And-Be-Curious-Project

Follow the instructions in the course for completing the group LBC project.

# Game Organizer Server

## Development Setup

### Setup and Configure for local dynamodb

Verify Docker is up and running
Run shell script to create local dynamodb on Docker

```bash
./local-dynamodb.sh
```

Run Game Organizer service via Gradle command bootRunDev

```bash
./gradlew bootRunDev
```

Run Dynamodb Admin and verify database tables appear

```bash
dynamodb-admin
```

Run the 'aws configure' command and enter random data for the access key, secret access key, and region.
Run aws cli command to verify dynamodb tables exist and aws cli commands work
NOTE: Pass in whatever port local dynamodb is running on which displayed after running the script to create it

```bash
aws dynamodb list-tables --endpoint-url http://localhost:8000
```

### Seed Game Organizer Service database tables using the below commands

```bash
aws dynamodb batch-write-item --request-items file://Collection_seeddata.json
aws dynamodb batch-write-item --request-items file://BoardGame_seeddata.json
aws dynamodb batch-write-item --request-items file://MagicTheGathering_seeddata.json

```