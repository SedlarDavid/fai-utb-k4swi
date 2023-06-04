#include <QKeyEvent>
#include <QMouseEvent>
#include <QPainter>
#include "pong.h"

const int WIDTH = 800;    // Width of the game window
const int HEIGHT = 400;   // Height of the game window
const int PADDLE_WIDTH = 10;    // Width of the paddles
const int PADDLE_HEIGHT = 60;   // Height of the paddles
const int BALL_RADIUS = 10;     // Radius of the ball
const int PADDLE_SPEED = 5;     // Speed of the paddles
const int BALL_SPEED = 2;       // Speed of the ball

Pong::Pong(QWidget *parent) : QWidget(parent) {
    setFixedSize(WIDTH, HEIGHT);

    ballX = WIDTH / 2;
    ballY = HEIGHT / 2;
    ballDX = BALL_SPEED;
    ballDY = BALL_SPEED;

    paddle1Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    paddle2Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;

    score1 = 0;
    score2 = 0;

    timerId = startTimer(10);   // Timer interval: 10 milliseconds
}

void Pong::paintEvent(QPaintEvent *event) {
    QPainter painter(this);

    // Set the background color to light blue
    painter.fillRect(rect(), QColorConstants::Svg::lightblue);

    // Draw the paddles
    painter.fillRect(0, paddle1Y, PADDLE_WIDTH, PADDLE_HEIGHT, QColorConstants::Svg::orangered);
    painter.fillRect(WIDTH - PADDLE_WIDTH, paddle2Y, PADDLE_WIDTH, PADDLE_HEIGHT, QColorConstants::Svg::orangered);

    // Draw the ball
    painter.setBrush(Qt::white);
    painter.drawEllipse(ballX - BALL_RADIUS, ballY - BALL_RADIUS, BALL_RADIUS * 2, BALL_RADIUS * 2);

    // Draw the scores
    painter.setPen(Qt::black);
    painter.setFont(QFont("Arial", 20));
    painter.drawText(100, 30, QString::number(score1));
    painter.drawText(WIDTH - 100, 30, QString::number(score2));
}

void Pong::timerEvent(QTimerEvent *event) {
    // Move the ball
    ballX += ballDX;
    ballY += ballDY;

    // Check collision with paddles
    if (ballX <= PADDLE_WIDTH && ballY >= paddle1Y && ballY <= paddle1Y + PADDLE_HEIGHT) {
        ballDX = BALL_SPEED;
    } else if (ballX >= WIDTH - PADDLE_WIDTH - BALL_RADIUS * 2 && ballY >= paddle2Y && ballY <= paddle2Y + PADDLE_HEIGHT) {
        ballDX = -BALL_SPEED;
    }

    // Check collision with top and bottom walls
    if (ballY <= 0 || ballY >= HEIGHT - BALL_RADIUS * 2) {
        ballDY = -ballDY;
    }

    // Check scoring
    if (ballX < 0) {
        ++score2;
        resetBall();
    } else if (ballX > WIDTH - BALL_RADIUS * 2) {
        ++score1;
        resetBall();
    }

    // Move the AI paddle
    if (ballY < paddle2Y + PADDLE_HEIGHT / 2) {
        paddle2Y -= PADDLE_SPEED;
    } else if (ballY > paddle2Y + PADDLE_HEIGHT / 2) {
        paddle2Y += PADDLE_SPEED;
    }

    update();
}

void Pong::mouseMoveEvent(QMouseEvent *event) {
    // Move the player paddle with the mouse
    paddle1Y = event->y() - PADDLE_HEIGHT / 2;

    // Restrict the paddle movement within the window boundaries
    if (paddle1Y < 0) {
        paddle1Y = 0;
    } else if (paddle1Y > HEIGHT - PADDLE_HEIGHT) {
        paddle1Y = HEIGHT - PADDLE_HEIGHT;
    }
}

void Pong::keyPressEvent(QKeyEvent *event) {
    // Pause or resume the game when the space key is pressed
    if (event->key() == Qt::Key_Space) {
        if (timerId == -1) {
            timerId = startTimer(10);
        } else {
            killTimer(timerId);
            timerId = -1;
        }
    }
}

void Pong::resetBall() {
    ballX = WIDTH / 2;
    ballY = HEIGHT / 2;
    ballDX = BALL_SPEED;
    ballDY = BALL_SPEED;
}
