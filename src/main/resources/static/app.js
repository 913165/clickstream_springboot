const output = document.getElementById("output");

async function sendEvent(elementId) {
  const payload = {
    eventType: "click",
    elementId,
    page: window.location.pathname,
    x: null,
    y: null,
  };

  const response = await fetch("/api/events", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload),
  });

  if (!response.ok) {
    throw new Error("Unable to send event");
  }
}

async function loadRecent() {
  const response = await fetch("/api/events/recent");
  if (!response.ok) {
    throw new Error("Unable to load events");
  }

  const events = await response.json();
  output.textContent = JSON.stringify(events, null, 2);
}

document.querySelectorAll(".buttons button").forEach((button) => {
  button.addEventListener("click", async () => {
    try {
      await sendEvent(button.id);
      await loadRecent();
    } catch (error) {
      output.textContent = error.message;
    }
  });
});

document.getElementById("refresh").addEventListener("click", async () => {
  try {
    await loadRecent();
  } catch (error) {
    output.textContent = error.message;
  }
});

