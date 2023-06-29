from fastapi import FastAPI, UploadFile, File
from minio import Minio
from dotenv import load_dotenv
from moviepy.editor import VideoFileClip
import io
import os
from minio.error import S3Error

from os import environ as env

load_dotenv()


app = FastAPI()


@app.get("/")
async def root():
    return {"message": "Hello World Alessandra"}

@app.get("/convert/{file}")
async def convert_mp4ToMp3(file: str):
    arquivo = "/tmp/downloads/" + file
    arquivo_convertido = arquivo.removesuffix("mp4") + "mp3"

    client = Minio(endpoint="minio:9000", access_key = env.get("MINIO_ROOT_USER"), secret_key = env.get("MINIO_ROOT_PASSWORD"), secure=False)
    try:
        client.fget_object(env.get("BUCKET_NAME"), file, file_name)
        video_to_audio(file_name, file_name_converted)

        with open(arquivo_convertido, "rb") as f:
            file_data = f.read()

        stream = io.BytesIO(file_data)

        client.put_object(bucket_name=env.get("BUCKET_NAME"), object_name="convertidos/" + arquivo_convertido.split("/")[-1], data=stream, length=len(file_data), content_type="audio/mpeg")

        return{"message": "Conversão feita com sucesso"}
    except Exception:
            return {"message" : "Erro ao realizar a conversão"}

def video_to_audio(video_file, audio_file):
    video_clip = VideoFileClip(video_file)
    audio_clip = video_clip.audio
    audio_clip.write_audiofile(audio_file)
    audio_clip.close()
    video_clip.close()