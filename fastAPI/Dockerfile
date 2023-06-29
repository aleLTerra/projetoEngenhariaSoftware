FROM python:3.10-slim

RUN apt update -y 
RUN apt install -y libsndfile1-dev

WORKDIR /app

COPY . /app

RUN pip install -r requirements.txt

EXPOSE 8000

CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]
