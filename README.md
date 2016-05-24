# Summary

meta-mpd-server support to build a MPD server machine.

This layer supports :

* static IP address in local.conf
* CIFS mount at boot
* MPD musicdir location
* DAC definition for MPD configuration(for Raspberry Pi)

## Dependency

This layers depdends on :

* meta-oe
* meta-python
* meta-networking
* meta-multimedia

and systemd.

# How to use

In order to use this layer, add the following settings to your local.conf.

then `bitbake core-image-minimal`. If your machine is Raspberry Pi, `bitbake rpi-basic-image`.


## systemd

```txt
# use systemd instead of sysvinit
DISTRO_FEATURES_append = " systemd"
VIRTUAL-RUNTIME_init_manager = "systemd"
DISTRO_FEATURES_BACKFILL_CONSIDERED = "sysvinit"
VIRTUAL-RUNTIME_initscripts = ""
```

## CIFS

```txt
IMAGE_INSTALL_append = " cifs-utils cifs-mount-networkd"
CIFS_SHARED_DIR = "//remote_pc/shared_dir"
CIFS_MOUNT_DIR = "/mnt/point"
CIFS_USER = "username"
CIFS_PASSWD = "password"
CIFS_VERS = "3.0"
```

## MPD

```txt
# mpd
LICENSE_FLAGS_WHITELIST = "commercial"
IMAGE_INSTALL_append = " mpd"

# MPD Music Directory
IMAGE_INSTALL_append = " mpd-musicdir"
MPD_MUSIC_DIR = "/mnt/point"
```

## CONNMAN

```txt
IMAGE_INSTALL_append = " connman \
		       	 connman-wait-online \
			 connman-ipv4-conf \
"
#SYSTEMD_SERVICE_append_pn-connman-wait-online = "connman-wait-online.service"
SYSTEMD_AUTO_ENABLE_pn-connman-wait-online = "enable"

# static IPv4 address for connman
CONNMAN_IPv4 = "XXX.XXX.XXX.XXX/YYY.YYY.YYY.YYY"
CONNMAN_IPv4_NS = "ZZZ.ZZZ.ZZZ.ZZZ"
```

`XXX.XXX.XXX.XXX` is IP address of target machine.

`YYY.YYY.YYY.YYY` is subnet mask.

`ZZZ.ZZZ.ZZZ.ZZZ` is IP address of Name Server.

# Maintainers

* Yusuke Mitsuki <mickey.happygolucky@gmail.com>
