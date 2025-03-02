FROM python:3.12

WORKDIR /app

COPY . .

RUN pip install flask

CMD ["python3", "app.py"]

