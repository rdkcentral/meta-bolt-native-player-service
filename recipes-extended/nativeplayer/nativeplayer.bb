SUMMARY = "Standalone native player for RDK-E"
DESCRIPTION = "A reference native  player for RDK-E"
HOMEPAGE = "https://github.com/rdkcentral/nativeplayerservice"
SECTION = "apps"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b17644be83787a32dcf1830fa4b0c64b"

PV = "1.0.0"

SRC_URI = "git://github.com/rdkcentral/nativeplayerservice;nobranch=1;protocol=https"
SRCREV = "7126639e5d43bcb200875dbf5142d30adad34be0"


S = "${WORKDIR}/git"
DEPENDS = "rpcserver aamp gstreamer1.0 jsoncpp glib-2.0 firebolt-cpp-client"
RDEPENDS_${PN} = "rpcserver aamp"

inherit cmake pkgconfig

EXTRA_OECMAKE += "-DPRIVATE_CONNECTION=ON "

