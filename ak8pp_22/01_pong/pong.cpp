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

}

void Pong::timerEvent(QTimerEvent *event) {

}

void Pong::mouseMoveEvent(QMouseEvent *event) {

}

void Pong::keyPressEvent(QKeyEvent *event) {

}

void Pong::resetBall() {

}
