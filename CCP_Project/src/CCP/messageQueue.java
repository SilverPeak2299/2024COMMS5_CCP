package CCP;

import org.json.simple.JSONObject;

// linked list with inset at the top, only holds 10 elemetns
public class messageQueue {
    int size;
    message root;

    messageQueue() {
        root = null;
        size = 0;
    }

    void addMessage(JSONObject msg) {
        size++;

        if (root == null) {
            root = new message(null);
            return;
        }

        root = new message(msg, root);

        //clearing old messages
        if (size > 10) {
            message current = root;

            while (current.getNext() != null) {
                current = current.getNext();
            }

            current.setNext(null);
            size = 10;
        }
    }

    int lastSeqNO() {
        return root.seqNo;
    }


    message peakMessage() {
        return root;
    }


    class message {
        private JSONObject msg;
        private message next;

        int seqNo;

        message(JSONObject msg) {
            this.msg = msg;
            next = null;
            seqNo = Integer.parseInt(jsonHandler.searchJSON(msg, "sequence_number"));
        }

        message(JSONObject msg, message next) {
            this.msg = msg;
            this.next = next;
        }

        public message getNext() {
            return next;
        }

        public void setNext(message next) {
            this.next = next;
        }

        public JSONObject getMsg() {
            return msg;
        }
    }
}
