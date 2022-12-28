
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

    // Make the paint() function virtual
    virtual void paint(QPainter *painter, const QStyleOptionViewItem &option, const QModelIndex &index) const;

private:
    Album m_album;
};
