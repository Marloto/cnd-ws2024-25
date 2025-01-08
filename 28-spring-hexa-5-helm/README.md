Build image before:

```bash
cd posts-service
docker build -t cnd/hexa-posts:latest .
```

Check chart, maybe fix values.yaml in root folder:

```bash
helm install --dry-run --debug hexa-posts ./hexa-posts -f ./values.yaml
```

Install chart:

```bash
helm install hexa-posts ./hexa-posts -f ./values.yaml
```

Check cluster

```bash
kubectl get all
kubectl describe ...
kubectl logs ...
```

Run tests from `test.http`

Remove chart with:


```bash
helm uninstall hexa-posts
```


