SUMMARY = "JSON RPC server library with Websocket transport support. The work is done in scope of sessionmgr JSON RPC 2.0/WS API but library is implemented in a generic way to be used by other applications."
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=175792518e4ac015ab6696d16c4f607e"

DEPENDS = "boost websocketpp jsoncpp jsonrpc"
RDEPENDS:${PN} += "jsoncpp jsonrpc"
SRCREV = "c9966b73f3d75afbfc2312b6e45d950bad84f56a"

SRC_URI = "git://github.com/rdkcentral/rpcserver;nobranch=1;protocol=https"

S = "${WORKDIR}/git/"

inherit pkgconfig cmake


