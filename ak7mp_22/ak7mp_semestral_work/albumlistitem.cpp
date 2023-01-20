#include "albumlistitem.h"

AlbumListItem::AlbumListItem(const Album &album, QListWidget *parent)
    : QListWidgetItem(parent), m_album(album)
{
    setText(m_album.name());
}
