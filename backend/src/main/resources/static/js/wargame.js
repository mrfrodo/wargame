document.addEventListener("DOMContentLoaded", function () {
    const hexSize = 30;
    const hexHeight = Math.sqrt(3) * hexSize;
    const svg = document.getElementById("hexmap");
    const startButton = document.getElementById("start-game-button");

    function drawHex(x, y, terrain) {
        const points = [];
        for (let i = 0; i < 6; i++) {
            const angle = Math.PI / 3 * i;
            const px = x + hexSize * Math.cos(angle);
            const py = y + hexSize * Math.sin(angle);
            points.push(`${px},${py}`);
        }

        const hex = document.createElementNS("http://www.w3.org/2000/svg", "polygon");
        hex.setAttribute("points", points.join(" "));
        hex.setAttribute("class", `hex ${terrain}`);
        svg.appendChild(hex);
    }

    function renderHexMap() {
        svg.innerHTML = ""; // clear old map
        fetch("/api/start-game")
            .then(res => res.json())
            .then(map => {
                map.forEach(tile => {
                    const { q, r, terrain } = tile;
                    const x = hexSize * Math.sqrt(3) * (q + r / 2);
                    const y = hexSize * 3 / 2 * r;
                    drawHex(x + 50, y + 50, terrain);
                });
            });
    }

    if (startButton) {
        startButton.addEventListener("click", renderHexMap);
    }
});
