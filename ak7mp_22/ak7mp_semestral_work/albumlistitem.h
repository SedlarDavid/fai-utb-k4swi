
#include "album.h"
#include <QApplication>
#include <QListWidget>
#include <QListWidgetItem>
#include <QPainter>
#include <QPixmap>



class AlbumListItem : public QListWidgetItem
{
public:
    AlbumListItem (const Album &album, QListWidget *parent = nullptr);

private:
    Album m_album;
};
