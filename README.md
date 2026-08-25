# TestVTB

## Запуск инфраструктуры

```bash
docker compose up -d --build
```

## Запуск теста

```bash
docker compose --profile load-test run --rm k6 run -o experimental-prometheus-rw --tag testid=kafka-load-test /scripts/kafka-test.js
```
