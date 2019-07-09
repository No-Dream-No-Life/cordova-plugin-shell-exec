var exec = require("cordova/exec");

exports.rootExec = function (cmd, callback) {
  exec(callback, function (err) {
    callback({exitStatus: 100, output: err});
  }, "ShellExec", "rootExec", cmd);
};
