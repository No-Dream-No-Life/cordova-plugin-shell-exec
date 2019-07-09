var exec = require("cordova/exec");

exports.exec = function (cmd, callback) {
  exec(callback, function (err) {
    callback({exitStatus: 100, output: err});
  }, "ShellExec", "exec", [cmd]);
};

exports.setTime = function (time, success, error) {
  cordova.exec(success, error, "ShellExec", "setTime", [time]);
};
