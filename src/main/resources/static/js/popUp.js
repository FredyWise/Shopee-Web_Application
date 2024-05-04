// showConfirmationPopup('title', $(this).attr('href')); return false;

function showConfirmationPopup(action, actionLink) {
    var overlay = $("<div>").addClass("overlay");
    var popup = $("<div>").addClass("confirm-popup");
    var heading = $("<h3>").text("Are you sure you want to " + action.toLowerCase() + "?");
    var buttonsContainer = $("<div>").addClass("confirm-popup-buttons");
    var confirmButton = $("<button>").addClass("confirm-button").text(action);
    var cancelButton = $("<button>").addClass("cancel-button").text("Cancel");

    confirmButton.on("click", function () {
        executeAction(actionLink);
    });

    cancelButton.on("click", function () {
        closeConfirmationPopup();
    });

    buttonsContainer.append(confirmButton, cancelButton);
    popup.append(heading, buttonsContainer);
    overlay.append(popup);
    $("body").append(overlay);
}

function closeConfirmationPopup() {
    $(".overlay").remove();
}

function executeAction(actionLink) {
    window.location.href = actionLink;
}