package cld.jcoder.demo.domain;

import  java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// models the GUI on http://localhost:8080/ dashboard.html
public class GithubRepoEvent {

    private final Type type; // 'Action' on dashboard.html // it is an enum
    private final OffsetDateTime creationTime;
    private final Actor actor;
    private final Issue issue;

    // @JsonCreator over the construcotr + @JsonProperty gives control over the unmarshalling process
    @JsonCreator //to deserialize some JSON that doesn't exactly match the target entity
    // eg json has created_at and we have creationTime
    public GithubRepoEvent(@JsonProperty("event") String type,
                           @JsonProperty("created_at") OffsetDateTime creationTime,
                           @JsonProperty("actor") Actor actor,
                           @JsonProperty("issue") Issue issue) {
        this.type = Type.valueFrom(type);
        this.creationTime = creationTime;
        this.actor = actor;
        this.issue = issue;
    }
    /* The class makes a selection of properties of the json we get
    from https://api.github.com/repos/spring-io/sagan/issues/events
{
id: 5492304032,
node_id: "CE_lADOAJeYfM49evz5zwAAAAFHXeig",
url: "https://api.github.com/repos/spring-io/sagan/issues/events/5492304032",
+actor: {},
event: "closed",
commit_id: null,
commit_url: null,
created_at: "2021-10-20T14:20:57Z",
+issue: {},
performed_via_github_app: null
},
*/

    public Type getType() {
        return type;
    }

    public OffsetDateTime getCreationTime() {
        return creationTime;
    }

    public Actor getActor() {
        return actor;
    }

    public Issue getIssue() {
        return issue;
    }
//////////////////////////// enum ////////////////////////
    public enum Type {

        CLOSED("closed"),
        REOPENED("reopened"),
        SUBSCRIBED("subscribed"),
        UNSUBSCRIBED("unsubscribed"),
        MERGED("merged"),
        REFERENCED("referenced"),
        MENTIONED("mentioned"),
        ASSIGNED("assigned"),
        UNASSIGNED("unassigned"),
        LABELED("labeled"),
        UNLABELED("unlabeled"),
        MILESTONED("milestoned"),
        DEMILESTONED("demilestoned"),
        RENAMED("renamed"),
        LOCKED("locked"),
        UNLOCKED("unlocked"),
        HEAD_REF_DELETED("head_ref_deleted"),
        HEAD_REF_RESTORED("head_ref_restored"),
        CONVERTED_NOTE_TO_ISSUE("converted_note_to_issue"),
        MOVED_COLUMNS_IN_PROJECT("moved_columns_in_project"),
        COMMENT_DELETED("comment_deleted"),
        REVIEW_REQUESTED("review_requested"),
        HEAD_REF_FORCE_PUSHED("head_ref_force_pushed"),
        BASE_REF_FORCE_PUSHED("base_ref_force_pushed"),
        REVIEW_REQUEST_REMOVED("review_request_removed");

        private String type;

        Type(String type) {
            this.type = type;
        }

        static Type valueFrom(String type) {
            for (Type value : values()) {
                if (type.equals(value.type)) {
                    return value;
                }
            }
            throw new IllegalArgumentException(
                    "'" + type + "' is not a valid event type");
        }
    }

}