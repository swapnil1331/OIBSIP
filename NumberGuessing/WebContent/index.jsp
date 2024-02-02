<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Number Guessing Game</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
            background-color: darkgrey;
        }

        .container {
            text-align: center;
        }

        button {
            padding: 10px;
            margin-top: 10px;
            cursor: pointer;
            background-color: royalblue;
        }

        input {
            padding: 5px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Number Guessing Game</h1>
        <p>Guess a number between 1 and 100:</p>
        <input type="number" id="guessInput" min="1" max="100">
        <button onclick="checkGuess()">Submit Guess</button>
        <p id="message" style="background:red"></p>
        <p>Attempts: <span id="attempts">0</span></p>
        <button onclick="resetGame()">Restart Game</button>
    </div>
    <script>
        // JavaScript code
        let secretNumber = generateSecretNumber();
        let attempts = 0;

        function generateSecretNumber() {
            return Math.floor(Math.random() * 100) + 1;
        }

        function checkGuess() {
            const guessInput = document.getElementById("guessInput");
            const message = document.getElementById("message");
            const attemptsDisplay = document.getElementById("attempts");

            const userGuess = parseInt(guessInput.value);

            if (isNaN(userGuess) || userGuess < 1 || userGuess > 100) {
                message.textContent = "Please enter a valid number between 1 and 100.";
                return;
            }

            attempts++;

            if (attempts <= 3) {
                if (userGuess === secretNumber) {
                    message.textContent = `Congratulations! You guessed the correct number ${secretNumber} in ${attempts} attempts.`;
                    guessInput.disabled = true;
                } else {
                    message.textContent = userGuess < secretNumber ? "Too low. Try again." : "Too high. Try again.";
                }
            } else {
                message.textContent = "Sorry, you've reached the maximum number of attempts. The correct number was " + secretNumber + ".";
                guessInput.disabled = true;
            }

            attemptsDisplay.textContent = attempts;
        }

        function resetGame() {
            const guessInput = document.getElementById("guessInput");
            const message = document.getElementById("message");
            const attemptsDisplay = document.getElementById("attempts");

            guessInput.value = "";
            guessInput.disabled = false;
            message.textContent = "";
            attemptsDisplay.textContent = "0";

            // Generate a new secret number for the next game
            secretNumber = generateSecretNumber();

            attempts = 0;
        }
    </script>
</body>
</html>
