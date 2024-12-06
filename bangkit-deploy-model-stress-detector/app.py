from flask import Flask, request, jsonify
import numpy as np
import tensorflow as tf
from tensorflow.keras.models import load_model
from PIL import Image
import io

app = Flask(__name__)

# Fungsi untuk memuat model .h5
def load_model_from_h5(model_path):
    try:
        model = load_model(model_path)
        print("Model loaded successfully.")
        print(f"Model input shape: {model.input_shape}")  # Menambahkan print untuk input shape model
        return model
    except Exception as e:
        print(f"Error loading model: {e}")
        raise e

# Load the Keras model (sesuaikan nama file model .h5 Anda)
model = load_model_from_h5('Model_StressDetection_V6.h5')

@app.route('/')
def home():
    return "Welcome to the Flask API for Image Prediction!"

@app.route('/predict', methods=['POST'])
def predict():
    try:
        # Pastikan ada file gambar dalam request
        if 'file' not in request.files:
            return jsonify({
                'status': 'error',
                'message': 'No file part'
            }), 400
        
        file = request.files['file']
        
        if file.filename == '':
            return jsonify({
                'status': 'error',
                'message': 'No selected file'
            }), 400
        
        # Membaca gambar dari file
        image = Image.open(file.stream)

        # Mengubah gambar menjadi grayscale jika model mengharapkannya
        if image.mode != 'L':  # 'L' adalah mode untuk gambar grayscale
            image = image.convert('L')

        # Normalisasi gambar
        img_array = np.array(image) / 255.0  # Normalize image
        img_array = np.expand_dims(img_array, axis=-1)  # Menambahkan channel dimensi (1, 48, 48, 1)
        img_array = np.expand_dims(img_array, axis=0)  # Tambahkan dimensi batch (1, 48, 48, 1)

        # Prediksi dengan model
        prediction = model.predict(img_array)
        
        # Ubah hasil prediksi ke dalam format yang diinginkan (misalnya, argmax untuk klasifikasi)
        result = float(prediction[0][0])

        return jsonify({
            'status': 'success',
            'prediction': result
        })
    
    except Exception as e:
        return jsonify({
            'status': 'error',
            'message': str(e)
        }), 400

if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000, debug=True)
