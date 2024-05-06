import React from "react";
import { useState } from "react";

function CheckboxRec(props) {
  let prefName = props.prefName;
  let prefIndex = props.prefIndex;
  let userRecList = props.userRecList;
  const [isChecked, setIsChecked] = useState(false);

  const handleChange = () => {
    setIsChecked(!isChecked);
    console.log(isChecked);
  };

  let tmpList = userRecList;
  console.log(tmpList);
  if (userRecList.includes(prefIndex)) {
    return (
      <>
        <input
          className="topicOption"
          id={prefName}
          type="checkbox"
          name={prefIndex}
          value="true"
          checked={!isChecked}
          onChange={(e) => {
            setIsChecked(!isChecked);
            //e.target._valueTracker.setValue(isChecked);
            console.log(prefName, e.target._valueTracker.getValue());
          }}
        ></input>
        <label htmlFor={prefName}>{prefName}</label>
        <br />
      </>
    );
  } else {
    return (
      <>
        <input
          className="prefOption"
          id={prefName}
          type="checkbox"
          name={prefIndex}
          value="true"
          checked={isChecked}
          onChange={(e) => {
            setIsChecked(!isChecked);
            //e.target._valueTracker.setValue(!isChecked);
            console.log(prefName, e.target._valueTracker.getValue());
          }}
        ></input>
        <label htmlFor={prefName}>{prefName}</label>
        <br />
      </>
    );
  }
}

export default CheckboxRec;
