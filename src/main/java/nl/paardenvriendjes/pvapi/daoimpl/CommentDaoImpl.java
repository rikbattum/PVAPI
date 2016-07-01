package nl.paardenvriendjes.pvapi.daoimpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import nl.paardenvriendjes.pvapi.domain.Comment;
import nl.paardenvriendjes.pvapi.service.AbstractDaoService;

@Repository
@Transactional

public class CommentDaoImpl extends AbstractDaoService<Comment> {

	public CommentDaoImpl() {
		super(Comment.class);
	}
}
