# Python Example

Commands depend on your env, e.g., python, python3 or python3.12

## First Run / Setup

```
# creates venv (second param) folder for virtual env
python -m venv venv
# switch to env
source venv/bin/activate
# install flask
pip3.12 install flask
# start
python app.py
```

## Run

```
python app.py
```

## Test

```
curl -v http://localhost:5600/hello/abc
```