package org.apache.cordova.shell.exec;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellExec extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("rootExec")) {
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    Process p = null;
                    StringBuffer output = new StringBuffer();
                    DataOutputStream os = null;
                    int exitStatus = 100;
                    try {
                        p = Runtime.getRuntime().exec("su");
                        os = new DataOutputStream(p.getOutputStream());
                        for (int i = 0; i < args.length(); i++) {
                            os.writeBytes(args.getString(i) + "\n");
                        }
//                        os.writeBytes("exit\n");
                        os.flush();

                        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        String line = "";
                        while ((line = reader.readLine()) != null) {
                            output.append(line + "\n");
                            //p.waitFor();
                        }
                        //exitStatus = p.exitValue();
                        exitStatus = p.waitFor();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (os != null) {
                                os.close();
                            }
                            p.destroy();
                        } catch (Exception e) {
                        }
                    }

                    try {
                        JSONObject json = new JSONObject();
                        json.put("exitStatus", exitStatus);
                        json.put("output", output.toString());
                        callbackContext.success(json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callbackContext.success();
                    }
                }
            });
            return true;
        }
        return false;
    }

}
