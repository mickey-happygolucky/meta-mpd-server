#!/bin/sh

MPD_MUSIC_DIR_SRC=""
MPD_MUSIC_DIR_DST="/var/lib/mpd/music"

if [ -d ${MPD_MUSIC_DIR_DST} ] ; then
    rmdir ${MPD_MUSIC_DIR_DST}
fi

if [ -d "${MPD_MUSIC_DIR_SRC}" ] ; then
    ln -s "${MPD_MUSIC_DIR_SRC}" "${MPD_MUSIC_DIR_DST}"
else
    echo "${MPD_MUSIC_DIR_SRC} is not directory."
    exit 1
fi

echo "done."
