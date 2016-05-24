SUMMARY = "Connman config to static IPv4 address setup wired interface"
DESCRIPTION = "ConnMan configuration to set up to Wired network interface"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

FILES_${PN} = "${localstatedir}/*"

#                IP Address     / subnet mask
# CONNMAN_IPv4 = xxx.xxx.xxx.xxx/xxx.xxx.xxx.xxx

IPv4CFGF = "${D}${localstatedir}/lib/connman/wired.config"

do_install() {
    if [ -n "${CONNMAN_IPv4}" ] ; then
	install -d ${D}${localstatedir}/lib/connman

	echo "[global]" > ${IPv4CFGF}
	echo "Name = Wired" >> ${IPv4CFGF}
	echo "Description = Wired network configuration" >> ${IPv4CFGF}
	echo "" >> ${IPv4CFGF}
	echo "[service_ether]" >> ${IPv4CFGF}
	echo "Type = ethernet" >> ${IPv4CFGF}
	echo "IPv4 = ${CONNMAN_IPv4}" >> ${IPv4CFGF}
	
	if [ -n "${CONNMAN_IPv4_NS}" ] ; then
	    echo "Nameservers = ${CONNMAN_IPv4_NS}" \
		 >> ${D}${localstatedir}/lib/connman/wired.config
	fi
    else
	install -d ${D}${localstatedir}/lib/connman
	touch ${D}${localstatedir}/dummy
    fi
}
