#ifndef PONG_H
#define PONG_H

#include <QWidget>

class Pong : public QWidget {
    Q_OBJECT
public:
    explicit Pong(QWidget *parent = nullptr);

protected:
    void paintEvent(QPaintEvent *event) override;
    void timerEvent(QTimerEvent *event) override;
    void mouseMoveEvent(QMouseEvent *event) override;
    void keyPressEvent(QKeyEvent *event) override;

private:
    void resetBall();

    int ballX;
    int ballY;
    int ballDX;
    int ballDY;

    int paddle1Y;
    int paddle2Y;

    int score1;
    int score2;

    int timerId;
};

#endif // PONG_H
