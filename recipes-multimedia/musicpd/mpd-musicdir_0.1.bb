SUMMARY = "Replace mpd music directory."
DESCRIPTION = "Generate a unit file to replace music directory."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit systemd

RDPENDS = "mpd"

SRC_URI = "file://mpd-musicdir.service \
	   file://replace-mpd-musicdir.sh \
"

FILES_${PN} = "${sysconfdir}/systemd/system/* \
	       ${bindir}/* \
"

CIFS_MOUNT_NAME = "${@ '${CIFS_MOUNT_DIR}'.replace('/', '-')[1:]}"
MPD_MUSICDIR_UNIT = "${D}${sysconfdir}/systemd/system/mpd-musicdir.service"
SYSTEMD_SERVICE_${PN} = "mpd-musicdir.service"

do_install() {
    if [ -n "${MPD_MUSIC_DIR}" ] ; then
       install -d ${D}${sysconfdir}/systemd/system
       install -m 0644 ${WORKDIR}/mpd-musicdir.service ${D}${sysconfdir}/systemd/system
       if [ -n "${CIFS_MOUNT_NAME}" ] ; then
       	  sed -i 's|Before=mpd.service|Before=mpd.service\nAfter=${CIFS_MOUNT_NAME}.mount|' \
	      ${MPD_MUSICDIR_UNIT}
       fi

       install -d ${D}${bindir}
       install -m 0755 ${WORKDIR}/replace-mpd-musicdir.sh ${D}${bindir}
       sed -i -e 's|MPD_MUSIC_DIR_SRC=""|MPD_MUSIC_DIR_SRC="${MPD_MUSIC_DIR}"|' \
       	   ${D}${bindir}//replace-mpd-musicdir.sh
    fi
}
