import React from "react";
import { useState } from "react";

function RecommendationComponent() {
  const [toggled, setToggle] = useState(false);

  const handleToggle = () => {
    setToggle(!toggled);
  };

  return (
    <div>
      <button className="recComp" onClick={handleToggle}>
        {toggled ? "ON" : "OFF"}
      </button>
    </div>
  )

}

export default RecommendationComponent;
