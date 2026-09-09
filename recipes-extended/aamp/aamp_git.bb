SUMMARY = "RDK AAMP component recipe specific for pure RDK IPA PoC purpose"
SECTION = "console/utils"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=97dd37dbf35103376811825b038fc32b"

PV = "3.3.9e"
PR = "r0"

SRCREV_FORMAT = "aamp"
SRCREV_aamp ?= "6369fdab6e0df6168108ee39c68e63a54b1ce5b4"

# Support to build from a different branch by overriding both AAMP_BRANCH and SRCREV_aamp to specific branch and revision.
AAMP_BRANCH ?= "develop"
CMF_GITHUB_BRANCH = "branch=${AAMP_BRANCH}"

DEPENDS += "curl libdash libxml2 cjson readline gstreamer1.0 "

DEPENDS += "rialto-ocdm-link"
RDEPENDS:${PN} += "rialto-gstreamer libsoup-2.4 \
    gstreamer1.0-plugins-good-isomp4 \
    gstreamer1.0-plugins-base-app \
    gstreamer1.0-plugins-base-playback \
    gstreamer1.0-plugins-good-soup \
    gstreamer1.0-plugins-good-matroska \
    gstreamer1.0-plugins-base-audioconvert \
    gstreamer1.0-plugins-base-audioresample \
    gstreamer1.0-plugins-base-gio \
    gstreamer1.0-plugins-base-videoconvert \
    gstreamer1.0-plugins-base-videoscale \
    gstreamer1.0-plugins-base-volume \
    gstreamer1.0-plugins-base-typefindfunctions \
    gstreamer1.0-plugins-good-audiofx \
    gstreamer1.0-plugins-good-audioparsers \
    gstreamer1.0-plugins-good-autodetect \
    gstreamer1.0-plugins-good-avi \
    gstreamer1.0-plugins-good-deinterlace \
    gstreamer1.0-plugins-good-interleave \
    gstreamer1.0-plugins-bad-dash \
    gstreamer1.0-plugins-bad-mpegtsdemux \
    gstreamer1.0-plugins-bad-smoothstreaming \
    gstreamer1.0-plugins-bad-videoparsersbad \
"

#gstreamer1.0-plugins-bad-opusparse 
inherit pkgconfig
inherit cmake

EXTRA_OECMAKE += " -DCMAKE_EXTERNAL_PLAYER_INTERFACE_DEPENDENCIES=0 -DCMAKE_USE_RIALTO=1 -DCMAKE_USE_THUNDER_OCDM_API_0_2=1 -DCMAKE_CDM_DRM=1 -DCMAKE_USE_OPENCDM_ADAPTER=1 -DCMAKE_USE_PLAYREADY=1 -DCMAKE_USE_WIDEVINE=1"

NO_RECOMMENDATIONS = "1"

SRC_URI = "git://github.com/rdkcentral/aamp.git;protocol=https;nobranch=1;name=aamp"
SRC_URI += "file://0001-disable-closedcaptions.patch"
S = "${WORKDIR}/git"

EXTRA_OECMAKE += " -DCMAKE_SYSTEMD_JOURNAL=0"
EXTRA_OECMAKE += " -DCMAKE_BUILD_TYPE=Debug"
# has impact here https://github.com/rdkcentral/aamp/blob/develop/CMakeLists.txt#L260
EXTRA_OECMAKE += " -DCMAKE_INBUILT_AAMP_DEPENDENCIES=1"

EXTRA_OECMAKE += " -DCMAKE_WPEWEBKIT_WATERMARK_JSBINDINGS=0 "
PACKAGES = "${PN} ${PN}-dev ${PN}-dbg"

FILES:${PN} += "${libdir}/lib*.so"
FILES:${PN} += "${libdir}/aamp-cli"
FILES:${PN} += "${libdir}/aamp/lib*.so"
FILES:${PN} +="${libdir}/gstreamer-1.0/lib*.so"
FILES:${PN}-dbg +="${libdir}/gstreamer-1.0/.debug/*"
INSANE_SKIP:${PN} = "dev-so"

do_install:append() {
    # remove the static library if it is installed, 
    # CMakelist in aamp code installing static lib below line should avoid build error 
    rm -f ${D}${libdir}/libtsb.a
}

