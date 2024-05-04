document.getElementById('image').addEventListener('change', function (event) {
    var imagePreview = document.getElementById('image-preview');
    imagePreview.style.display = 'block';
    imagePreview.src = URL.createObjectURL(event.target.files[0]);
    var imageOriginal = document.getElementById('image-original');
    imageOriginal.style.display = 'none';
});