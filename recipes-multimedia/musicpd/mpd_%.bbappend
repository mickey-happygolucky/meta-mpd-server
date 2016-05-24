SYSTEMD_SERVICE_${PN}_remove = "mpd.socket"
FILES_${PN} += "${systemd_unitdir}/system/mpd.socket"

PACKAGECONFIG_append = " id3tag"

do_install_append() {
    if [ "${HIFIBERRY_DIGI}" = "1" ] ; then
       echo 'audio_output {' >> ${D}/${sysconfdir}/mpd.conf
       echo '        type            "alsa"' >> ${D}/${sysconfdir}/mpd.conf
       echo '        name            "MyAlsa"' >> ${D}/${sysconfdir}/mpd.conf
       echo '        device          "hw:0,0"' >> ${D}/${sysconfdir}/mpd.conf
       echo '        mixer_control   "Digital"' >> ${D}/${sysconfdir}/mpd.conf
       echo '}' >> ${D}/${sysconfdir}/mpd.conf
    fi
    if [ "${HIFIBERRY_DAC}" = "1" -o "${HIFIBERRY_DACPLUS}" = "1" ] ; then
       echo 'audio_output {' >> ${D}/${sysconfdir}/mpd.conf
       echo '        type            "alsa"' >> ${D}/${sysconfdir}/mpd.conf
       echo '        name            "MyAlsa"' >> ${D}/${sysconfdir}/mpd.conf
       echo '        device          "hw:0,0"' >> ${D}/${sysconfdir}/mpd.conf
       echo '}' >> ${D}/${sysconfdir}/mpd.conf
    fi
}
