# Consul key value spring boot example

### in [docker-env](docker-env) you can find [consul-compose.yml](docker-env%2Fconsul-compose.yml)

### it's needded for test env

Now you can access the front view of the consul for example by : http://localhost:8500/ui/dc1/kv

After creation a test env consul, you need to create key-value record.

SMTH like : base.currency
inside you can switch to JSON and add value :

    [
        {"code": "USD", "description": "Dollars"},
        {"code": "EUR", "description": "Euro"},
        {"code": "RUR", "description": "Russian rubles"}
    ]

Then save it

