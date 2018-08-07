package com.bcm.pojo;

public enum UserType {

	Admin {
		@Override
		public boolean isPermitted(String function) {
			return true;
		}
	},
	BCMManager {
		@Override
		public boolean isPermitted(String function) {
			if ("users".equals(function)) {
				return false;
			}
			return true;
		}
	},
	Partner {
		@Override
		public boolean isPermitted(String function) {
			if ("users".equals(function)) {
				return false;
			}
			return true;
		}
	};

	public abstract boolean isPermitted(String function);
}