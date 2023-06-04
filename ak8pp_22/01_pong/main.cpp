#include <QApplication>
#include "pong.h"

int main(int argc, char *argv[]) {
    QApplication app(argc, argv);

    Pong pong;
    pong.show();

    return app.exec();
}
