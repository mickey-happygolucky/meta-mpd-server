SUMMARY = "Mount CIFS at boot"
DESCRIPTION = "Generate a mount unit file to mount remote via cifs at boot."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit systemd

MOUNT_NAME = "${@ '${CIFS_MOUNT_DIR}'.replace('/', '-')[1:]}"
CIFS_MOUNT_UNIT = "${D}${sysconfdir}/systemd/system/${MOUNT_NAME}.mount"

SYSTEMD_SERVICE_${PN} = "${MOUNT_NAME}.mount"

do_install() {
    if [ -n "${CIFS_SHARED_DIR}" -a -n ${CIFS_MOUNT_DIR} ] ; then
        install -d ${D}${sysconfdir}/systemd/system

	if [ ${CIFS_SEC_NTLM} = "1" ] ; then
	   SEC=",sec=ntlm"
	else
	   SEC=""
	fi

	if [ -n ${CIFS_VERS} ] ; then
	   VERS=",vers=${CIFS_VERS}"
	else
	   VERS=""
	fi

	echo "[Unit]" > ${CIFS_MOUNT_UNIT}
	echo "Description=Mount Share at boot" >> ${CIFS_MOUNT_UNIT}
	echo "Requires=connman.service" >> ${CIFS_MOUNT_UNIT}
        echo "After=connman-wait-online.service" >> ${CIFS_MOUNT_UNIT}
	echo "" >> ${CIFS_MOUNT_UNIT}
	echo "[Mount]" >> ${CIFS_MOUNT_UNIT}
	echo "What=${CIFS_SHARED_DIR}" >> ${CIFS_MOUNT_UNIT}
	echo "Where=${CIFS_MOUNT_DIR}" >> ${CIFS_MOUNT_UNIT}
	echo "Options=username=${CIFS_USER},password=${CIFS_PASSWD},iocharset=utf8,rw,x-systemd.automount${SEC}${VERS}" >> ${CIFS_MOUNT_UNIT}
	echo "Type=cifs" >> ${CIFS_MOUNT_UNIT}
	echo "TimeoutSec=30" >> ${CIFS_MOUNT_UNIT}
	echo "" >> ${CIFS_MOUNT_UNIT}
	echo "[Install]" >> ${CIFS_MOUNT_UNIT}
	echo "WantedBy=multi-user.target" >> ${CIFS_MOUNT_UNIT}
    fi
}
