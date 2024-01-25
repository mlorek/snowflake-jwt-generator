# snowflake-jwt-generator
JWT Genarator for Snowflake Database

# Key Pair Generation

## Private Key

```openssl genrsa 2048 | openssl pkcs8 -topk8 -inform PEM -out rsa_key.p8 -nocrypt```

## Public Key

```openssl rsa -in rsa_key.p8 -pubout -out rsa_key.pub```
