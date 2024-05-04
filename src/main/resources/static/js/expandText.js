var descriptions = document.querySelectorAll('.toggle-show');

descriptions.forEach(function(description) {
    description.addEventListener('click', function() {
        description.classList.toggle('expanded');
    });
});