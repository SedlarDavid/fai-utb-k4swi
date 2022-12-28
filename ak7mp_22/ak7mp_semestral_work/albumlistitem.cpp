#include "albumlistitem.h"

AlbumListItem::AlbumListItem(const Album &album, QListWidget *parent)
    : QListWidgetItem(parent), m_album(album)
{
}

void AlbumListItem::paint(QPainter *painter, const QStyleOptionViewItem &option, const QModelIndex &index) const
{
    // Draw the background
    paint(painter, option, index);

    // Draw the name and age
    painter->drawText(option.rect, Qt::AlignVCenter, m_album.name());
}
