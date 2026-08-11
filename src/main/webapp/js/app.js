function confirmCancel() {
    return confirm("Cancel this booking? The seat will become available again.");
}

document.addEventListener("DOMContentLoaded", () => {
    const dateInput = document.querySelector('input[type="date"]');
    if (dateInput) {
        const today = new Date().toISOString().split("T")[0];
        dateInput.min = today;
    }
});
